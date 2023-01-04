package chess_layer;

import board_layer.Board;
import board_layer.Position;
import chessPieces.Bishop;
import chessPieces.Horse;
import chessPieces.King;
import chessPieces.Pawn;
import chessPieces.Queen;
import chessPieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;

	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	public ChessPiece[][] getPieces(){
		ChessPiece[][]mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns();j++) {
				mat[i][j]= (ChessPiece)board.piece(i, j);
			}
		}
		return mat;
	}
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(0, 0));
		board.placePiece(new Horse(board, Color.WHITE), new Position(0, 1));
		board.placePiece(new Bishop(board, Color.WHITE), new Position(0, 2));
		board.placePiece(new King(board, Color.WHITE), new Position(0, 3));
		board.placePiece(new Queen(board, Color.WHITE), new Position(0, 4));
		board.placePiece(new Bishop(board, Color.WHITE), new Position(0, 5));
		board.placePiece(new Horse(board, Color.WHITE), new Position(0, 6));
		board.placePiece(new Rook(board, Color.WHITE), new Position(0, 7));
		
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 0));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 1));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 2));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 3));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 4));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 5));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 6));
		board.placePiece(new Pawn(board, Color.WHITE), new Position(1, 7));

		
		board.placePiece(new Rook(board, Color.BLACK), new Position(7, 0));
		board.placePiece(new Horse(board, Color.BLACK), new Position(7, 1));
		board.placePiece(new Bishop(board, Color.BLACK), new Position(7, 2));
		board.placePiece(new Queen(board, Color.BLACK), new Position(7, 3));
		board.placePiece(new King(board, Color.BLACK), new Position(7, 4));
		board.placePiece(new Bishop(board, Color.BLACK), new Position(7, 5));
		board.placePiece(new Horse(board, Color.BLACK), new Position(7, 6));
		board.placePiece(new Rook(board, Color.BLACK), new Position(7, 7));
		
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 0));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 1));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 2));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 3));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 4));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 5));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 6));
		board.placePiece(new Pawn(board, Color.BLACK), new Position(6, 7));		
	}
}
