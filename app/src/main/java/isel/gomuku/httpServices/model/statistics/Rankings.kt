package isel.gomuku.httpServices.model.statistics

class Rankings (  val bestPlayers: List<BestPlayerRanking>,
                  val victories: List<VictoriesRanking>,
                  val mostGames: List<GamesRanking>,
                  val mostTime: List<TimePlayedRanking>,
                  val playerDefeats: List<DefeatsRanking>,
                  val prevPage: String?,
                  val nextPage: String?
)