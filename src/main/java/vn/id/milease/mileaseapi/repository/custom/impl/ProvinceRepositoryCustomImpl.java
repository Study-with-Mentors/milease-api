package vn.id.milease.mileaseapi.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.Province;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.QDistrict;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.QProvince;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.QWard;
import vn.id.milease.mileaseapi.repository.custom.ProvinceRepositoryCustom;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProvinceRepositoryCustomImpl implements ProvinceRepositoryCustom {
    private static final QProvince province = QProvince.province;
    private static final QDistrict district = QDistrict.district;
    private static final QWard ward = QWard.ward;

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void postConstruct() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Province> findAllWithDistricts() {
        // It can only fetch Province and Districts at the same time.
        // If I add fetchJoin() after Ward join, it will throw MultipleBagFetchException exception.
        // TODO [Khanh, P3]: Find a solution for fetching all 3 entities at the same time.
        return queryFactory.selectFrom(province)
                .distinct()
                .leftJoin(province.districts, district).fetchJoin()
                .leftJoin(district.wards, ward)
                .fetch();
    }
}
