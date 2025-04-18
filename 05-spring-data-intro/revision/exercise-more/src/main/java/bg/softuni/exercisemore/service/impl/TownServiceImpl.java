package bg.softuni.exercisemore.service.impl;

import bg.softuni.exercisemore.constants.ExceptionMessageTemplates;
import bg.softuni.exercisemore.entities.Town;
import bg.softuni.exercisemore.exception.EntityNotFoundForIdException;
import bg.softuni.exercisemore.repository.TownRepository;
import bg.softuni.exercisemore.service.CountryService;
import bg.softuni.exercisemore.service.TownService;
import bg.softuni.exercisemore.util.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TownServiceImpl implements TownService {

    private final static String TEMPLATE = "town";
    private final static int INIT_COUNT = 6;

    private final TownRepository townRepository;

    private final CountryService countryService;

    @Autowired
    public TownServiceImpl(
            TownRepository townRepository,
            CountryService countryService
    ) {
        this.townRepository = townRepository;
        this.countryService = countryService;
    }

    @Override
    public Town persist(Town town) {
        return this.townRepository.save(town);
    }

    @Override
    public void seed() {
        if (this.townRepository.count() > 0) return;

        for (int i = 1; i <= INIT_COUNT; i++) {
            this.townRepository.save(new Town().setName(TEMPLATE + i)
                    .setCountry(this.countryService.getRandom()));
        }
    }

    @Override
    public Town getRandom() {
        int index = GenerationUtil.RANDOM.nextInt((int) this.townRepository.count()) + 1;

        return this.townRepository.findById(index)
                .orElseThrow(() -> new EntityNotFoundForIdException(
                        String.format(ExceptionMessageTemplates.TOWN_NOT_FOUND_FOR_ID, index)
                ));
    }
}
