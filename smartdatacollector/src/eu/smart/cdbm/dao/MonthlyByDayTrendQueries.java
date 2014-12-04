package eu.smart.cdbm.dao;

public class MonthlyByDayTrendQueries {
	
	
	//Query to get hystorical trend per alarm per day of week
	
	public static String sql_sound_level  = "SELECT  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=1, 1, NULL)) `MONDAY`,  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=2, 1, NULL)) `TUESDAY`,  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=3, 1, NULL)) `WEDNSDAY`,  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=4, 1, NULL)) `THURSDAY`,  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=5, 1, NULL)) `FRIDAY`,  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=6, 1, NULL)) `SATURDAY`,  "
			+"COUNT(IF((`sound_level` = 1 ) AND day_of_week=7, 1, NULL)) `SUNDAY`  "
			+"from smartcop.trend  where month=? ";
	
	
	public static String sql_crowd_sound_score  = "SELECT  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=1, 1, NULL)) `MONDAY`,  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=2, 1, NULL)) `TUESDAY`,  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=3, 1, NULL)) `WEDNSDAY`,  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=4, 1, NULL)) `THURSDAY`,  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=5, 1, NULL)) `FRIDAY`,  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=6, 1, NULL)) `SATURDAY`,  "
			+"COUNT(IF((`crowd_sound_score` = 1 ) AND day_of_week=7, 1, NULL)) `SUNDAY`  "
			+"from smartcop.trend   where month=? ";
	
	public static String sql_traffic_sound_score  = "SELECT  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=1, 1, NULL)) `MONDAY`,  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=2, 1, NULL)) `TUESDAY`,  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=3, 1, NULL)) `WEDNSDAY`,  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=4, 1, NULL)) `THURSDAY`,  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=5, 1, NULL)) `FRIDAY`,  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=6, 1, NULL)) `SATURDAY`,  "
			+"COUNT(IF((`traffic_sound_score` = 1 ) AND day_of_week=7, 1, NULL)) `SUNDAY`  "
			+"from smartcop.trend   where month=? ";
	
	public static String sql_temperature_level  = "SELECT  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=1, 1, NULL)) `MONDAY`,  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=2, 1, NULL)) `TUESDAY`,  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=3, 1, NULL)) `WEDNSDAY`,  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=4, 1, NULL)) `THURSDAY`,  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=5, 1, NULL)) `FRIDAY`,  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=6, 1, NULL)) `SATURDAY`,  "
			+"COUNT(IF((`temperature_level` = 1 ) AND day_of_week=7, 1, NULL)) `SUNDAY`  "
			+"from smartcop.trend   where month=? ";
	
	
	public static String sql_crowd_density_score  = "SELECT  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=1, 1, NULL)) `MONDAY`,  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=2, 1, NULL)) `TUESDAY`,  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=3, 1, NULL)) `WEDNSDAY`,  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=4, 1, NULL)) `THURSDAY`,  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=5, 1, NULL)) `FRIDAY`,  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=6, 1, NULL)) `SATURDAY`,  "
			+"COUNT(IF((`crowd_density_score` = 1 ) AND day_of_week=7, 1, NULL)) `SUNDAY`  "
			+"from smartcop.trend   where month=? ";
	
	
	//fine Query to get hystorical trend per alarm per day of week

}
