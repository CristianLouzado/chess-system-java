package chess_layer;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import board_layer.Board;
import board_layer.Piece;
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
	private static boolean check;
	private static boolean checkMate;
	private ChessPiece enPassantVunerable;
	private ChessPiece promoted;
	
	private List <Piece> piecesOnTheBoard = new ArrayList<>();
	private List <Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn =1;
		currentPlayer =Color.WHITE;
		initialSetup();
	}
	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	public static boolean getCheck() {
		return check;
	}
	public static boolean getCheckMate() {
		return checkMate;
	}
	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
	}
	public ChessPiece getPromoted() {
		return promoted;
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
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	public ChessPiece perfomeChessmove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece=makeMove(source, target);
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		promoted =null;
		if(movedPiece instanceof Pawn) {
			if(movedPiece.getColor()== Color.BLACK&& target.getRow()==0||movedPiece.getColor()== Color.WHITE&& target.getRow()==7) {
				promoted =(ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer)))? true :false;
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate=true;
		}else {
			nextTurn();
		}
		if(movedPiece instanceof Pawn &&(target.getRow()==source.getRow()-2||target.getRow()==source.getRow()+2)) {
			enPassantVunerable = movedPiece;
		}else {
			enPassantVunerable=null;
		}
		return (ChessPiece) capturedPiece;
	}
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted==null) {
			throw new IllegalStateException("There is no piece to be promoted.");
		}
		if(!type.equals("B")&& !type.equals("H")&& !type.contentEquals("R")&& !type.contentEquals("Q")) {
			throw new InvalidParameterException("Invalid type of promotion.");
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("H")) return new Horse(board,color);
		if(type.contentEquals("R")) return new Rook(board,color);
		return new Queen(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece !=null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		if (source.getRow()==0) {
			if(p instanceof King &&target.getColumn()==source.getColumn()-2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()-3);
				Position targetR = new Position (source.getRow(), source.getColumn()-1);
				ChessPiece rook =(ChessPiece)board.removePiece(sourceR);
				board.placePiece(rook, targetR);
				rook.increaseMoveCount();
			}
			
			if(p instanceof King &&target.getColumn()==source.getColumn()+2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()+4);
				Position targetR = new Position (source.getRow(), source.getColumn()+1);
				ChessPiece rook =(ChessPiece)board.removePiece(sourceR);
				board.placePiece(rook, targetR);
				rook.increaseMoveCount();
			}
		}else {
			if(p instanceof King &&target.getColumn()==source.getColumn()+2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()+3);
				Position targetR = new Position (source.getRow(), source.getColumn()+1);
				ChessPiece rook =(ChessPiece)board.removePiece(sourceR);
				board.placePiece(rook, targetR);
				rook.increaseMoveCount();
			}
			
			if(p instanceof King &&target.getColumn()==source.getColumn()-2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()-4);
				Position targetR = new Position (source.getRow(), source.getColumn()-1);
				ChessPiece rook =(ChessPiece)board.removePiece(sourceR);
				board.placePiece(rook, targetR);
				rook.increaseMoveCount();
			}
		}
		if (p instanceof Pawn) {
			if (source.getColumn()!= target.getColumn() && capturedPiece==null) {
				Position pawnPosition;
				if(p.getColor()== Color.BLACK) {
					pawnPosition= new Position(target.getRow()+1, target.getColumn());
				}else {
					pawnPosition= new Position(target.getRow()-1, target.getColumn());
				}
				capturedPiece=board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece){
		ChessPiece p =(ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece !=null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}if (source.getRow()==0) {
			if(p instanceof King &&target.getColumn()==source.getColumn()-2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()-3);
				Position targetR = new Position (source.getRow(), source.getColumn()-1);
				ChessPiece rook =(ChessPiece)board.removePiece(targetR);
				board.placePiece(rook, sourceR);
				rook.decreaseMoveCount();;
			}
			
			if(p instanceof King &&target.getColumn()==source.getColumn()+2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()+4);
				Position targetR = new Position (source.getRow(), source.getColumn()+1);
				ChessPiece rook =(ChessPiece)board.removePiece(targetR);
				board.placePiece(rook, sourceR);
				rook.decreaseMoveCount();;
			}
		}else {
			if(p instanceof King &&target.getColumn()==source.getColumn()+2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()+3);
				Position targetR = new Position (source.getRow(), source.getColumn()+1);
				ChessPiece rook =(ChessPiece)board.removePiece(targetR);
				board.placePiece(rook, sourceR);
				rook.decreaseMoveCount();;
			}
			
			if(p instanceof King &&target.getColumn()==source.getColumn()-2) {
				Position sourceR = new Position (source.getRow(), source.getColumn()-4);
				Position targetR = new Position (source.getRow(), source.getColumn()-1);
				ChessPiece rook =(ChessPiece)board.removePiece(targetR);
				board.placePiece(rook, sourceR);
				rook.decreaseMoveCount();;
			}
		}
		if (p instanceof Pawn) {
			if (source.getColumn()!= target.getColumn() && capturedPiece==null) {
				ChessPiece pawn= (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if(p.getColor()== Color.BLACK) {
					pawnPosition= new Position(3, target.getColumn());
				}else {
					pawnPosition= new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
		
	}
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		if (currentPlayer !=((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to the target position.");
		}
	}
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	private Color opponent(Color color) {
		return (color ==Color.WHITE)? Color.BLACK : Color.WHITE;
	}
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	public boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean [][] mat = p.possibleMoves();
			for(int i=0; i<board.getRows(); i++) {
				for(int j=0;j<board.getColumns();j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	private void initialSetup() {
		placeNewPiece('a', 8, new Rook(board, Color.WHITE));
		placeNewPiece('b', 8, new Horse(board, Color.WHITE));
		placeNewPiece('c', 8, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 8, new King(board, Color.WHITE, this));	
		placeNewPiece('e', 8, new Queen(board, Color.WHITE));	
		placeNewPiece('f', 8, new Bishop(board, Color.WHITE));	
		placeNewPiece('g', 8, new Horse(board, Color.WHITE));	
		placeNewPiece('h', 8, new Rook(board, Color.WHITE));
		
		placeNewPiece('a', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('b', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('c', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('d', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('e', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('f', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('g', 7, new Pawn(board, Color.WHITE,this));
		placeNewPiece('h', 7, new Pawn(board, Color.WHITE,this));
		
		placeNewPiece('a', 1, new Rook(board, Color.BLACK));
		placeNewPiece('b', 1, new Horse(board, Color.BLACK));
		placeNewPiece('c', 1, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 1, new Queen(board, Color.BLACK));	
		placeNewPiece('e', 1, new King(board, Color.BLACK, this));	
		placeNewPiece('f', 1, new Bishop(board, Color.BLACK));	
		placeNewPiece('g', 1, new Horse(board, Color.BLACK));	
		placeNewPiece('h', 1, new Rook(board, Color.BLACK));
		
		placeNewPiece('a', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('b', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('c', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('d', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('e', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('f', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('g', 2, new Pawn(board, Color.BLACK,this));
		placeNewPiece('h', 2, new Pawn(board, Color.BLACK,this));
	}
}
