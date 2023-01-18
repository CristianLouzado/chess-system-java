package chessPieces;

import board_layer.Board;
import chess_layer.ChessPiece;
import chess_layer.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}
	@Override
	public String toString() {
		return "K";
	}
	@Override
	public boolean[][] possibleMoves() {
		return null;
	}
}
