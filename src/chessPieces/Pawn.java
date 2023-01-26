package chessPieces;

import board_layer.Board;
import board_layer.Position;
import chess_layer.ChessMatch;
import chess_layer.ChessPiece;
import chess_layer.Color;

public class Pawn extends ChessPiece{

	private ChessMatch chessMatch;
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch=chessMatch;
	}
	@Override
	public String toString(){
	return "P";	
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		
		if (getColor()==Color.WHITE) {
			p.setValues(position.getRow()+1, position.getColumn());
			if (getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			p.setValues(position.getRow()+2, position.getColumn());
			Position p2 = new Position(position.getRow()+1, position.getColumn());
			if (getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)&&getBoard().positionExists(p2)&&!getBoard().thereIsAPiece(p2)&&getMoveCount()==0) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			p.setValues(position.getRow()+1, position.getColumn()-1);
			if (getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			p.setValues(position.getRow()+1, position.getColumn()+1);
			if (getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			//En Passant
			if(position.getRow()==5) {
				Position left = new Position (position.getRow(),position.getColumn()-1);
				if(getBoard().positionExists(left)&& isThereOpponentPiece(left)&& getBoard().piece(left)==chessMatch.getEnPassantVunerable()) {
					mat[left.getRow()][left.getColumn()]=true;
				}
				Position right = new Position (position.getRow(),position.getColumn()+1);
				if(getBoard().positionExists(right)&& isThereOpponentPiece(right)&& getBoard().piece(right)==chessMatch.getEnPassantVunerable()) {
					mat[right.getRow()][right.getColumn()]=true;
				}
			}
		}else {
			p.setValues(position.getRow()-1, position.getColumn());
			if (getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			p.setValues(position.getRow()-2, position.getColumn());
			Position p2 = new Position(position.getRow()-1, position.getColumn());
			if (getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)&&getBoard().positionExists(p2)&&!getBoard().thereIsAPiece(p2)&&getMoveCount()==0) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			p.setValues(position.getRow()-1, position.getColumn()-1);
			if (getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			p.setValues(position.getRow()-1, position.getColumn()+1);
			if (getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()]=true;
			}
			//En Passant
			if(position.getRow()==3) {
				Position left = new Position (position.getRow(),position.getColumn()-1);
				if(getBoard().positionExists(left)&& isThereOpponentPiece(left)&& getBoard().piece(left)==chessMatch.getEnPassantVunerable()) {
					mat[left.getRow()][left.getColumn()]=true;
				}
				Position right = new Position (position.getRow(),position.getColumn()+1);
				if(getBoard().positionExists(right)&& isThereOpponentPiece(right)&& getBoard().piece(right)==chessMatch.getEnPassantVunerable()) {
					mat[right.getRow()][right.getColumn()]=true;
				}
			}
		}
		return mat;
	}
}
