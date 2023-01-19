package chessPieces;

import board_layer.Board;
import chess_layer.ChessPiece;
import chess_layer.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
	}
	@Override
	public String toString(){
	return "P";	
	}
	@Override
	public boolean[][] possibleMoves() {
		return null;
	}
}
