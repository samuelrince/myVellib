package fr.ecp.IS1220.beribu;


public class Vlibre implements Card{
	private User user;
	
	public Vlibre(User user) {
		super();
		this.user = user;
		user.subscribe(this);
	}
	
	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return this.user;
	}
	@Override
	public double cost(Duration duration, String bicycleType) {
		// TODO Auto-generated method stub
		int costDuration = duration.getDuration();
		if (costDuration>3600) {
			if (costDuration-3600 > this.user.getTimeCreditBalance().getDuration()) {
				costDuration -= this.user.getTimeCreditBalance().getDuration();
			}
			else {
				costDuration = 3600;
			}
			if (bicycleType == "Mechanical") {
				return (costDuration-1)/3600;
			}
			else if (bicycleType == "Electrical") {
				return ((costDuration-1)/3600+1.0/2.0)*2;
			}
		}
		else {
			if (bicycleType == "Mechanical") {
				return 0;
			}
			else if (bicycleType == "Electrical") {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public void updateTimeCreditBalance(Duration duration, String bicycleType, 
			boolean plusStation) {
		// TODO Auto-generated method stub
		int costDuration = duration.getDuration();
		if (costDuration>3600) {
			if (costDuration-3600 > this.user.getTimeCreditBalance().getDuration()) {
				this.user.setTimeCreditBalance(0);
			}
			else {
				this.user.addTimeCreditBalance(0,3600-costDuration);
			}
		}
		
		if (plusStation) {
			this.user.addTimeCreditBalance(5);
		}
	}
	
}