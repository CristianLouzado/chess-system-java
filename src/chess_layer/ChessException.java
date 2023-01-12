package chess_layer;

import board_layer.BoardException;

public class ChessException extends BoardException {
	private static final long serialVersionUID = 1L;
	
	public ChessException(String msg) {
		super(msg);
	}
}
