package application;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess_layer.ChessException;
import chess_layer.ChessMatch;
import chess_layer.ChessPiece;
import chess_layer.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List <ChessPiece> captured = new ArrayList<>();
		
		while (!ChessMatch.getCheckMate()) {
		try {
			UI.clearScreen();
			UI.printMatch(chessMatch, captured);
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(sc);
			
			boolean [][] possibleMoves = chessMatch.possibleMoves(source);
			UI.clearScreen();
			UI.printBoard(chessMatch.getPieces(), possibleMoves);
			System.out.println(); 
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(sc);
			
			ChessPiece capturedPiece = chessMatch.perfomeChessmove(source, target);
			
			if(capturedPiece !=null) {
				captured.add(capturedPiece);
				}
			if(chessMatch.getPromoted()!= null) {
				System.out.print("Enter piece for promotion(B/H/R/Q): ");
				String type = sc.nextLine().toUpperCase();
				while(!type.equals("B")&& !type.equals("H")&& !type.contentEquals("R")&& !type.contentEquals("Q")) {
					System.out.print("Invalid value! Enter piece for promotion(B/H/R/Q): ");
					type = sc.nextLine().toUpperCase();
				}
				chessMatch.replacePromotedPiece(type);
			}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		
	}

}
