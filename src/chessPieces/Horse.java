package chessPieces;

import board_layer.Board;
import chess_layer.ChessPiece;
import chess_layer.Color;

public class Horse extends ChessPiece{

	public Horse(Board board, Color color) {
		super(board, color);
	}
	@Override
	public String toString() {
		return "H";
	}
}
