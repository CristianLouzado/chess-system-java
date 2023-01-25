package chess_layer;

import board_layer.Board;
import board_layer.Piece;
import board_layer.Position;

public abstract class ChessPiece extends Piece {
	private Color color;
	private int moveCount;
	
	public Color getColor() {
		return color;
	}

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}

}