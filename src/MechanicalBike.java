
public class MechanicalBike extends Bicycle {
	private long id;
	private String type = "Mechanical";
	private double speed = 15;
	
	public MechanicalBike() {
		super();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

}
