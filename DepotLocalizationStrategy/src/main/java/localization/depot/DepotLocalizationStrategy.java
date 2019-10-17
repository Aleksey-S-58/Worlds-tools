package localization.depot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import service.Localization;
import service.Square;

@Service
public class DepotLocalizationStrategy implements Localization {

	@Override
	public List<Square> getSquare(long hight, double latitude, double longitude) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}
