package tesi.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tesi.model.Prodotto;

public class DAO {
	
	public List<Prodotto> getProdottiDB() {
		
		String sql = "SELECT productId FROM products";

		List<Prodotto> prodotti = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Prodotto p = new Prodotto(res.getString("productId"));
				prodotti.add(p);
			}

			conn.close();
			return prodotti;
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getStoricoDB(Prodotto prodotto) {
		
		String sql = "SELECT sum(quantity) as qty " +
                     "FROM storico " +
                     "WHERE productid = ? and x = 'VE' " +
                     "GROUP BY year(date), month(date) " +
                     "ORDER BY year(date), month(date);";

		List<Integer> storico = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, prodotto.getCodice());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				storico.add(res.getInt("qty"));
			}

			conn.close();
			return storico;
		} 
		catch (SQLException e) {	
			e.printStackTrace();
			return null;
		}
	}

	public void aggiornaStorico(Prodotto prodotto, int[] tbs) {
		
//		String insertProd = "INSERT INTO storico(contatore, productId, date, x, quantity) "
//				+ "VALUES(?,?,?,?,?);";
//	
	}
}
