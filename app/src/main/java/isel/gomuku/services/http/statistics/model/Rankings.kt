package isel.gomuku.services.http.statistics.model

class Rankings (val bestPlayers: List<BestPlayerRanking>,
                val victories: List<VictoriesRanking>,
                val mostGames: List<GamesRanking>,
                val mostTime: List<TimePlayedRanking>,
                val playerDefeats: List<DefeatsRanking>,
                val prevPage: String?,
                val nextPage: String?
)