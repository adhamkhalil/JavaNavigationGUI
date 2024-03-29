package homework1;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The 
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint {
	private final int latitude;
	private final int longitude;

	/** Minimum value the latitude field can have in this class. **/
	public static final int MIN_LATITUDE  =  -90 * 1000000;
	    
	/** Maximum value the latitude field can have in this class. **/
	public static final int MAX_LATITUDE  =   90 * 1000000;
	    
	/** Minimum value the longitude field can have in this class. **/
	public static final int MIN_LONGITUDE = -180 * 1000000;
	    
	/** Maximum value the longitude field can have in this class. **/
	public static final int MAX_LONGITUDE =  180 * 1000000;

  	/**
   	 * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LATITUDE = 110.901;

  	/**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LONGITUDE = 93.681;

	/** million dollar baby . **/
	public static final int MILLION =   1000000;

	// Implementation hint:
	// Doubles and floating point math can cause some problems. The exact
	// value of a double can not be guaranteed except within some epsilon.
	// Because of this, using doubles for the equals() and hashCode()
	// methods can have erroneous results. Do not use floats or doubles for
	// any computations in hashCode(), equals(), or where any other time 
	// exact values are required. (Exact values are not required for length 
	// and distance computations). Because of this, you should consider 
	// using ints for your internal representation of GeoPoint. 

  	
  	// TODO Write abstraction function and representation invariant
	// Abs. Function for every GeoPoint p: (p represents a geographical point (Deg N/S, Deg E/W) )
	// 		if (p.latitude >= 0) then latitude = p.latitude/MILLION N else latitude = p.latitude/MILLION S
	//		if (p.longitude >= 0) then longitude = p.longitude/MILLION E else longitude = p.longitude/MILLION W

  	// Rep. Invariant for every GeoPoint p:
	// (MIN_LATITUDE <= p.latitude <= MAX_LATITUDE) and
	// (MIN_LONGITUDE <= p.longitude <= MAX_LONGITUDE)

	/**
	 * Checks if representation invariant holds for every GeoPoint p
	 * @throws AssertionError if representation invariant is violated
	 * */
	private void checkRep(){
//		assert (this != null) : "null GeoPoint";
		assert (latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) : "invalid latitude input";
		assert (longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE) : "invalid longitude input";
	}
  	/**
  	 * Constructs GeoPoint from a latitude and longitude.
     * @requires the point given by (latitude, longitude) in millionths
   	 *           of a degree is valid such that:
   	 *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * 	 		 (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
   	 * @effects constructs a GeoPoint from a latitude and longitude
     *          given in millionths of degrees.
   	 **/
  	public GeoPoint(int latitude, int longitude) {
		this.latitude = latitude;
  		this.longitude = longitude;
		checkRep();
	}

  	/**
	 * Constructs GeoPoint from  gp.latitude and gp.longitude
	 * @requires gs != null
  	 * @returns a new copied geo point from gs **/
  	public GeoPoint(GeoPoint gp){
  		// client should make sure the gp != null, not the developers.
  		latitude = gp.latitude;
  		longitude = gp.longitude;
  		checkRep();
	}
  	 
  	/**
     * Returns the latitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLatitude() {
  		checkRep();
		return latitude;
  	}


  	/**
     * Returns the longitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLongitude() {
  		checkRep();
		return longitude;
  	}


  	/**
     * Computes the distance between GeoPoints.
     * @requires gp != null
     * @return the distance from this to gp, using the flat-surface, near
     *         the Technion approximation.
     **/
  	public double distanceTo(GeoPoint gp) {
  		checkRep();
		double lat1 = ((double)this.latitude/MILLION), lat2 = ((double)gp.latitude/MILLION);
		double long1 = ((double)this.longitude/MILLION), long2 = ((double)gp.longitude/MILLION);
		double delta_long = Math.abs(long1 - long2);
		double delta_lat = Math.abs(lat1 - lat2);
		double arg1 = Math.pow(KM_PER_DEGREE_LATITUDE*delta_lat,2);
		double arg2 = Math.pow(KM_PER_DEGREE_LONGITUDE*delta_long,2);
		checkRep();
		return Math.sqrt(arg1+arg2);
  	}


  	/**
     * Computes the compass heading between GeoPoints.
     * @requires gp != null && !this.equals(gp)
     * @return the compass heading h from this to gp, in degrees, using the
     *         flat-surface, near the Technion approximation, such that
     *         0 <= h < 360. In compass headings, north = 0, east = 90,
     *         south = 180, and west = 270.
     **/
  	public double headingTo(GeoPoint gp) {
		 //	Implementation hints:
		 // 1. You may find the mehtod Math.atan2() useful when
		 // implementing this method. More info can be found at:
		 // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
		 //
		 // 2. Keep in mind that in our coordinate system, north is 0
		 // degrees and degrees increase in the clockwise direction. By
		 // mathematical convention, "east" is 0 degrees, and degrees
		 // increase in the counterclockwise direction. 
		checkRep();
		double lat1 = Math.toRadians(((double)this.latitude/MILLION)), lat2 = Math.toRadians(((double)gp.latitude/MILLION));
		double long1 = Math.toRadians(((double)this.longitude/MILLION)), long2 = Math.toRadians(((double)gp.longitude/MILLION));
		double delta_long = long2 - long1;
		double arg1 = Math.cos(lat2)*Math.sin(delta_long);
		double arg2 = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(delta_long);
		double deg = Math.toDegrees(Math.atan2(arg1,arg2));
		checkRep();
		return (deg < 0) ? (deg + 360) : deg;
  	}


  	/**
     * Compares the specified Object with this GeoPoint for equality.
     * @return gp != null && (gp instanceof GeoPoint) &&
     * 		   gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
  	public boolean equals(Object gp) {
  		checkRep();
		if((gp == null) || !(gp instanceof GeoPoint))
			return false;
		checkRep(); //srsly?
		return ((latitude == ((GeoPoint) gp).latitude) && longitude == ((GeoPoint) gp).longitude);
  	}


  	/**
     * Returns a hash code value for this GeoPoint.
     * @return a hash code value for this GeoPoint.
   	 **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// for improved performance.

    	return 1;
  	}


  	/**
     * Returns a string representation of this GeoPoint.
     * @return a string representation of this GeoPoint.
     **/
  	public String toString() {
  		checkRep();
  		return new String("(" + latitude + "," + longitude + ")");
  	}

}
