package com.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Game;
import com.backend.models.Tournament;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

// Display a game with all it's events
// http://stackoverflow.com/questions/10878243/sse-and-servlet-3-0

@WebServlet("/game")
public class GameController extends HttpServlet 
{
	private static final long serialVersionUID = 357914572265696822L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try (Essentials essentials = Essentials.createEssentials(request, response)) 
		{
			essentials.request.setAttribute("isLoggedIn", Boolean.FALSE);
			if (essentials.subject.isAuthenticated()) 
			{
				essentials.request.setAttribute("isLoggedIn", Boolean.TRUE);
			}

			essentials.request.setAttribute("liveRefresh", Boolean.FALSE);
			Game liveGame = Game.getLiveGame(essentials);

			Game game = null;
			String gameIdString = Helpers.getParameter("gameId", String.class, essentials);
			if (gameIdString == null) 
			{
				game = liveGame;

				if (game == null) 
				{
					// There's no live game actually, show the next planned
					// game.
					Tournament tournament = Tournament.getTournament(essentials);
					game = Tournament.getNextGame(tournament.games);
				}
				
				if( game != null )
				{
					essentials.request.setAttribute("liveRefresh", Boolean.TRUE);
				}
			} 
			else 
			{
				ObjectId gameId = Helpers.getParameter("gameId", ObjectId.class, essentials);
				if (gameId != null) 
				{
					game = essentials.database.findOne(Game.class, gameId);
				}
			}

			if (game != null) 
			{
				essentials.request.setAttribute("isLive", Boolean.valueOf(game.equals(liveGame)));
				essentials.request.setAttribute("game", game);
				essentials.request.getRequestDispatcher("/WEB-INF/frontend/game.jsp").forward(essentials.request, essentials.response);
			} 
			else 
			{
				// Game not found, display the schedule.
				essentials.response.sendRedirect("schedule");
			}
		}
	}
}