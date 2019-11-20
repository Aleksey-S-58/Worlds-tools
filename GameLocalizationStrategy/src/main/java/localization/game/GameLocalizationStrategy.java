package localization.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import service.Localization;
import service.Square;

@Service
public class GameLocalizationStrategy implements Localization {

	@Override
	public List<Square> getSquare(double hight, double latitude, double longitude) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}
