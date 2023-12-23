package isel.gomuku.httpServices.model.statistics

class Rankings (val bestPlayerRanking: List<BestPlayerRanking>,
                val victoriesRanking: List<VictoriesRanking>,
                val gamesRanking: List<GamesRanking>,
                val timePlayedRanking: List<TimePlayedRanking>,
                val defeatsRanking: List<DefeatsRanking>
    )