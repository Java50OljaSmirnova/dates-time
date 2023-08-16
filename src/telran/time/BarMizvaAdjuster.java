package telran.time;

import java.time.temporal.*;

public class BarMizvaAdjuster implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if(!temporal.isSupported(ChronoUnit.YEARS)) {
			throw new UnsupportedTemporalTypeException("must support years");
		}
		return temporal.plus(13, ChronoUnit.YEARS);
	}

}
