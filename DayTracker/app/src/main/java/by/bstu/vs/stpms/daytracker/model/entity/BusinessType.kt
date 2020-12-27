package by.bstu.vs.stpms.daytracker.model.entity

import by.bstu.vs.stpms.daytracker.R

enum class BusinessType(val id: Int) {
    CHILL(R.id.chill), WORK(R.id.work), STUDYING(R.id.studying), ROAD(R.id.road), LUNCH(R.id.lunch), SLEEP(R.id.sleep);

    companion object {
        fun findById(id: Int): BusinessType {
            return values()
                    .toList()
                    .firstOrNull { it.id == id } ?: CHILL
        }
    }

}