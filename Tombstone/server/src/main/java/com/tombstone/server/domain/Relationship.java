package com.tombstone.server.domain;

import static com.tombstone.server.domain.Gender.FEMALE;
import static com.tombstone.server.domain.Gender.MALE;

public enum Relationship
{
    UNKNOWN,

    HUSBAND,

    WIFE,

    SPOUSE,

    FATHER,

    MOTHER,

    SON,

    DAUGHTER,

    BROTHER,

    SISTER,

    GRANDFATHER,

    GRANDMOTHER,

    GRANDSON,

    GRANDDAUGHTER,

    UNCLE,

    AUNT,

    NEWPHEW,

    NIECE,

    FATHER_IN_LAW,

    MOTHER_IN_LAW,

    SON_IN_LAW,

    DAUGHTER_IN_LAW,

    BROTHER_IN_LAW,

    SISTER_IN_LAW,

    BOYFRIEND,

    GIRLFRIEND,

    COMMON_LAW;

    public Relationship getCorrespondingRelationship(
        final Relationship relationship,
        final Gender gender)
    {
        switch(relationship)
        {
            case UNKNOWN:
                return UNKNOWN;

            case HUSBAND:
                return (gender == MALE) ? HUSBAND : WIFE;

            case WIFE:
                return (gender == FEMALE) ? HUSBAND : WIFE;

            case SPOUSE:
                return SPOUSE;

            case FATHER:
            case MOTHER:
                return (gender == MALE) ? SON : DAUGHTER;

            case SON:
            case DAUGHTER:
                return (gender == MALE) ? FATHER : MOTHER;

            case BROTHER:
            case SISTER:
                return (gender == MALE) ? BROTHER : SISTER;

            case GRANDFATHER:
            case GRANDMOTHER:
                return (gender == MALE) ? GRANDSON : GRANDDAUGHTER;

            case GRANDSON:
            case GRANDDAUGHTER:
                return (gender == MALE) ? GRANDFATHER : GRANDMOTHER;

            case UNCLE:
            case AUNT:
                return (gender == MALE) ? NEWPHEW : NIECE;

            case NEWPHEW:
            case NIECE:
                return (gender == MALE) ? UNCLE : AUNT;

            case FATHER_IN_LAW:
            case MOTHER_IN_LAW:
                return (gender == MALE) ? SON_IN_LAW : DAUGHTER_IN_LAW;

            case SON_IN_LAW:
            case DAUGHTER_IN_LAW:
                return (gender == MALE) ? FATHER_IN_LAW : MOTHER_IN_LAW;

            case BROTHER_IN_LAW:
            case SISTER_IN_LAW:
                return (gender == MALE) ? BROTHER_IN_LAW : SISTER_IN_LAW;

            case BOYFRIEND:
            case GIRLFRIEND:
                return (gender == MALE) ? BOYFRIEND : GIRLFRIEND;

            case COMMON_LAW:
                return COMMON_LAW;

            default:
                return UNKNOWN;
        }
    }
}
