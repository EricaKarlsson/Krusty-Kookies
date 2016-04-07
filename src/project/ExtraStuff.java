package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ExtraStuff {

	public ArrayList<String> getIngredientList(String recipes) {
		PreparedStatement ps = null;
		ArrayList<String> dates = new ArrayList<String>();
		try {
			String sql = "SELECT ingName,amount FROM RecipeType where cookieName = ?";
//			ps = conn.prepareStatement(sql);
			ps.setString(1, recipes);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				String s = result.getString(1);
				dates.add(s);
			}
			return dates;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	public ArrayList<String> getIngredientInfo(String recipe, String ingredient) {
		PreparedStatement ps = null;
		ArrayList<String> perfInfo = new ArrayList<String>();
		try {
			String sql = "SELECT Iamount, amount from Ingredient natural join RecipeType where cookieName = ? and ingName = ?";
//			ps = conn.prepareStatement(sql);
			ps.setString(1, recipe);
			ps.setString(2, ingredient);
			ResultSet res = ps.executeQuery();

			if (res.next()) {
				String t = res.getString(1);
				String s = String.valueOf(res.getDouble(2));
				perfInfo.add(t);
				perfInfo.add(s);
				return perfInfo;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public int orderIngredient(String ingredient) {
		PreparedStatement ps = null;
		try {
//			conn.setAutoCommit(false);
			String sql = "INSERT INTO IngDelivery(amount, deliveryDate, ingName) VALUES (?, ?, ?)";
			String sql2 = "Select Iamount from Ingredient where ingName = ?";
//			ps = conn.prepareStatement(sql);
			ps.setString(1, ingredient);
//			ldt = LocalDateTime.now();
//			 String asd = ldt.toString();
			 StringBuilder date = new StringBuilder(); 
			 for (int i = 0; i <= 9; i++)
//			date.append(asd.charAt(i));
			ps.setString(1, sql2);
			ps.setString(2, date.toString());
			ps.setString(3, ingredient);
			ps.executeUpdate();
			ps.close();

			sql = "UPDATE Ingredient " + "set Iamount = Iamount + 1000 "
					+ "where ingName = ?";
//			ps = conn.prepareStatement(sql);
			ps.setString(1, ingredient);
			ps.executeUpdate();

			sql = "SELECT Iamount from Ingredient where ingName = ?";
//			ps = conn.prepareStatement(sql);
			ps.setString(1, ingredient);
			ResultSet res = ps.executeQuery();
			res.next();
			return res.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
//				conn.setAutoCommit(true);
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public ArrayList<String> getCustomerList(){
		PreparedStatement ps = null;
		ArrayList<String> dates = new ArrayList<String>();
		try {
			String sql = "SELECT customerName FROM Customer";
//			ps = conn.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				String s = result.getString(1);
				dates.add(s);
			}
			return dates;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

}
