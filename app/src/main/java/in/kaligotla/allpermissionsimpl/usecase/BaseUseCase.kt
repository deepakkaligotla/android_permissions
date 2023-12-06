package `in`.kaligotla.allpermissionsimpl.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}