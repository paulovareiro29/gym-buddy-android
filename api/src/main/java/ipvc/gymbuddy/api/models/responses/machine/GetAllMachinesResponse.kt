package ipvc.gymbuddy.api.models.responses.machine

import ipvc.gymbuddy.api.models.Machine

data class GetAllMachinesResponse (
    val machines: List<Machine>
)