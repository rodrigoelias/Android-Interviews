//package data
//
//import retrofit2.Call
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//
///**
// * Created by rodrigoelias on 28/02/2018.
// */
//
//interface CreditScoreService{
//    @GET("1/")
//    fun getScore(): Call<PokeAPIResponse>
//}
//
//
//override fun onFailure(call: Call<PokeAPIResponse>?, t: Throwable) {
//    t.printStackTrace()
//}
//
//override fun onResponse(call: Call<PokeAPIResponse>, response: Response<PokeAPIResponse>) {
//    if (response.isSuccessful) {
//        var s = response.body()
//
//        s?.let{ s.pokemon.forEach { println("name : ${it.name}") }}
//    } else {
//        System.out.println(response.errorBody());
//    }
//}
//
//
//val retrofit = Retrofit.Builder()
//        .baseUrl("https://pokeapi.co//api/v1/pokedex/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//val service = retrofit.create(CreditScoreService::class.java)
//
//val s = service.getScore()
////s.enqueue(this)
//
//
