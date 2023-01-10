package chessPieces;

import board_layer.Board;
import chess_layer.ChessPiece;
import chess_layer.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}
	@Override
	public String toString() {
		return "R";
	}
}