package bg.softuni.exercisemore.service.impl;

import bg.softuni.exercisemore.constants.ExceptionMessageTemplates;
import bg.softuni.exercisemore.entities.Country;
import bg.softuni.exercisemore.exception.EntityNotFoundForIdException;
import bg.softuni.exercisemore.repository.CountryRepository;
import bg.softuni.exercisemore.service.CountryService;
import bg.softuni.exercisemore.util.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private final static String TEMPLATE = "country";
    private final static int INIT_COUNT = 3;

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country persist(Country country) {
        return this.countryRepository.save(country);
    }

    @Override
    public void seed() {
        if (this.countryRepository.count() > 0) return;

        for (int i = 1; i <= INIT_COUNT; i++) {
            this.countryRepository.save(new Country().setName(TEMPLATE + i));
        }
    }

    @Override
    public Country getRandom() {
        int index = GenerationUtil.RANDOM.nextInt((int) this.countryRepository.count()) + 1;

        return this.countryRepository.findById(index)
                .orElseThrow(() -> new EntityNotFoundForIdException(
                        String.format(ExceptionMessageTemplates.COUNTRY_NOT_FOUND_FOR_ID, index)
                ));
    }
}
