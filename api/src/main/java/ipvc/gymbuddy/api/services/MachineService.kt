package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.interfaces.IMachineService
import ipvc.gymbuddy.api.models.requests.machine.CreateMachineRequest
import ipvc.gymbuddy.api.models.responses.machine.CreateMachineResponse
import ipvc.gymbuddy.api.models.responses.machine.GetAllMachinesResponse

class MachineService : HttpClient<IMachineService>(IMachineService::class.java) {

    suspend fun getMachines(): RequestResult<GetAllMachinesResponse> {
        return when (val response = request(api.getMachines())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllMachinesResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun createMachine(body: CreateMachineRequest): RequestResult<CreateMachineResponse> {
        return when (val response = request(api.createMachine(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateMachineResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}