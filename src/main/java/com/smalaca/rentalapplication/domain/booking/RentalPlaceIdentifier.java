package com.smalaca.rentalapplication.domain.booking;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class RentalPlaceIdentifier {
    private final RentalType rentalType;
    private final String rentalPlaceId;

    RentalPlaceIdentifier(RentalType rentalType, String rentalPlaceId) {
        this.rentalType = rentalType;
        this.rentalPlaceId = rentalPlaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RentalPlaceIdentifier that = (RentalPlaceIdentifier) o;

        return new EqualsBuilder().append(rentalType, that.rentalType).append(rentalPlaceId, that.rentalPlaceId).isEquals();
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(rentalType).append(rentalPlaceId).toHashCode();
    }
}
