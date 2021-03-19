#include <iostream>

#include "ChessBoardPrint.hpp"

using namespace std;

ChessBoardPrint::ChessBoardPrint(ChessBoard &board) {
	board.on_piece_move = [](const ChessBoard::Piece &piece, const string &from, const string &to) {
		cout << piece.type() << " is moving from " << from << " to " << to << endl;
	};

	board.on_piece_removed = [](const ChessBoard::Piece &piece, const string &square) {
		cout << piece.type() << " is being removed from " << square << endl;
	};
	board.on_lost_game = [](ChessBoard::Color color) {
		if (color == ChessBoard::Color::WHITE)
			cout << "Black";
		else
			cout << "White";
		cout << " won the game" << endl;
	};
	board.on_piece_move_invalid = [](const ChessBoard::Piece &piece, const string &from, const string &to) {
		cout << "can not move " << piece.type() << " from " << from << " to " << to << endl;
	};
	board.on_piece_move_missing = [](const string &square) {
		cout << "no piece at " << square << endl;
	};

	board.after_piece_move = [](vector<vector<unique_ptr<ChessBoard::Piece>>> &squares) {
		char letter = 97;

		//Print row numbers
		for(size_t n=1; n<squares.size()+1; n++) cout << "  " << n;
		cout << endl;

		//Prints the chessboard out with player positions.
		for(size_t i=0; i<squares.size(); ++i){
			std::cout << letter++; //Prints letters on the side
			for(size_t j=0; j<squares[i].size(); ++j){
				if(squares[i][j]){
					std::cout << "[" <<  squares[i][j]->player_visualization() << "]"; //Print piece in square.
				} else {
					std::cout << "[ ]";
				}
			}
			std::cout << std::endl;
		}

	};

}

