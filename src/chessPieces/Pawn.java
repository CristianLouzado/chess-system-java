package chessPieces;

import board_layer.Board;
import board_layer.Position;
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
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
		}
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		
		return mat;
	}
}
