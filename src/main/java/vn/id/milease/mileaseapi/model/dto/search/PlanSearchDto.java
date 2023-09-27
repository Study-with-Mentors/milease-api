package vn.id.milease.mileaseapi.model.dto.search;

import de.mobiuscode.nameof.Name;
import lombok.Getter;
import lombok.Setter;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
public class PlanSearchDto extends BaseSearchDto {
    private String name;
    /**
     * result will contain plan that have <code>startDate</code> > lowerDate
     */
    private LocalDateTime lowerDate;
    /**
     * result will contain plan that have <code>endDate</code> < endDate
     */
    private LocalDateTime upperDate;
    private PlanProperty orderBy = PlanProperty.START;
    // TODO [Duy, P2] Add province after the entity has add field province

    @Override
    public String getOrderBy() {
        return orderBy.getNameOfProperty();
    }

    public enum PlanProperty implements EntityProperty {
        NAME(Plan::getName),
        START(Plan::getStart),
        END(Plan::getEnd);

        private final Function<Plan, ?> property;

        PlanProperty(Function<Plan, ?> property) {
            this.property = property;
        }

        @Override
        public String getNameOfProperty() {
            return Name.of(Plan.class, property);
        }
    }
}
