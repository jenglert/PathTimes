package jre.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class RouteDataHelper {
	
	private static final String DATABASE_NAME = "the126bus.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "routes";

	private Context context;
	private SQLiteDatabase db;

	private SQLiteStatement insertStmt;
	private static final String INSERT = "insert into " + TABLE_NAME
			+ "(route, start_time) values (?, ?)";

	public RouteDataHelper(Context context) {
	      this.context = context;
	      OpenHelper openHelper = new OpenHelper(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT);
	}

	public long insert(String route, long startTime) {
	      this.insertStmt.bindString(1, route);
	      this.insertStmt.bindLong(2, startTime);
	      return this.insertStmt.executeInsert();
	   }

	public void deleteAll() {
		this.db.delete(TABLE_NAME, null, null);
	}
	
	public SortedSet<Long> nextFiveBuses(Date startTime, Station station, TrainDirection direction) {
		SortedSet<Long> nextBuses = new TreeSet<Long>();
		
		List<Route> appropriateRoutes = new ArrayList<Route>();
		for (Route route : Route.values()) {
			if ( route.getDirection().equals(direction)) {
				if (ArrayUtil.contains(route.getStations(), station)) {
					appropriateRoutes.add(route);
				}
			}
		}
		
		if (appropriateRoutes.size() <= 0) {
			throw new RuntimeException("Failed to find even one appropriate route.");
		}
		
		StringBuilder routeString = new StringBuilder();
		
		for (int i = 0; i < appropriateRoutes.size(); i++) {
			Route route = appropriateRoutes.get(i);
			routeString.append("'" + route.name() + "'");
			
			if (i < appropriateRoutes.size() - 2) {
				routeString.append(",");
			}
		}
		
		
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { 
				"start_time" }, "start_time > " + startTime.getTime()
				+ " and route in (" + routeString.toString() + ")", null, null,
				null, "start_time asc", "5");
		
		if (cursor.moveToFirst()) {
			do {
				nextBuses.add(cursor.getLong(0));
			} while (cursor.moveToNext());
		}
		
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return nextBuses;
	}

	public List<Map<String, Object>> selectAll() {
	      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "route, start_time" }, 
	        null, null, null, null, "start_time asc");
	      if (cursor.moveToFirst()) {
	         do {
	        	 	Map<String, Object> rowList = new HashMap<String, Object>();
	            rowList.put("route", cursor.getString(0));
	            rowList.put("start_time", cursor.getLong(1));
	            
	            list.add(rowList);
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return list;
	   }

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
	      public void onCreate(SQLiteDatabase db) {
	         db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY,  start_time integer, route text)");
	      }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
