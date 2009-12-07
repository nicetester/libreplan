/*
 * This file is part of ###PROJECT_NAME###
 *
 * Copyright (C) 2009 Fundación para o Fomento da Calidade Industrial e
 *                    Desenvolvemento Tecnolóxico de Galicia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.navalplanner.business.calendars.entities;

import org.joda.time.LocalDate;
import org.navalplanner.business.resources.entities.Resource;

/**
 * Calendar for a {@link Resource}.
 *
 * @author Manuel Rego Casasnovas <mrego@igalia.com>
 */
public class ResourceCalendar extends BaseCalendar {

    public static ResourceCalendar create() {
        return create(new ResourceCalendar(CalendarData.create()));
    }

    /**
     * Constructor for hibernate. Do not use!
     */
    public ResourceCalendar() {
    }

    private ResourceCalendar(CalendarData calendarData) {
        super(calendarData);
        CalendarAvailability calendarAvailability = CalendarAvailability
                .create(new LocalDate(), null);
        addNewCalendarAvailability(calendarAvailability);
    }

    @Override
    public Integer getWorkableHours(LocalDate date) {
        if (!isActive(date)) {
            return 0;
        }
        return super.getWorkableHours(date);
    }

}
