package isel.gomuku.services.http.statistics.model

import com.isel.gomokuApi.domain.model.statistcs.BestPlayerRanking
import com.isel.gomokuApi.domain.model.statistcs.DefeatsRanking
import com.isel.gomokuApi.domain.model.statistcs.GamesRanking
import com.isel.gomokuApi.domain.model.statistcs.TimePlayedRanking
import com.isel.gomokuApi.domain.model.statistcs.VictoriesRanking

class Rankings (val bestPlayers: List<BestPlayerRanking>,
                val victories: List<VictoriesRanking>,
                val mostGames: List<GamesRanking>,
                val mostTime: List<TimePlayedRanking>,
                val playerDefeats: List<DefeatsRanking>)