package io.defy.chicken.lover.util

import android.app.Application
import android.util.Log
import defy.tech.chickenlover.model.UserLikeRepository
import defy.tech.chickenlover.model.user.UserLikeDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RandomPickUtil {
    //val fbRepo = FavoriteBrandRepository.getInstance()
    //val ftRepo = FavoriteTypeRepository.getInstance()

    fun randomBrandPick(db: UserLikeDatabase, application: Application): String? {
        val repository = UserLikeRepository(application).apply {
            getLikeBrand()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError{ Log.d("randomBrandPick","onError") }
                .doOnSubscribe { }
                .doOnSuccess { Log.d("randomBrandPick","onSuccess") }
                .doOnComplete { Log.d("randomBrandPick","onComplete") }
                .subscribe { likeBrand->

                }.dispose()
        }
        /*

        db.userLikeDao()
        val userLikeBrandList = db.userLikeDao()

        var fbCheckedList: ArrayList<FavoriteBrandData>? = ArrayList()
        for (item in fbList) {
            if (item.checked) fbCheckedList?.add(item)
        }

        var selectedFb: FavoriteBrandData? = null

        fbCheckedList?.let {
            val fbSize = fbCheckedList.size

            /*
             * 선호하는 브랜드 , 종류를 모두 고른 유저라면
             */
            if (fbSize > 0) {
                fbCheckedList.let {
                    selectedFb = getRandomInFb(it)
                }
                return selectedFb?.brandName
            } else {
                return "empty"
            }
        }
*/
        return "empty"
    }

    fun randomTypePick(): String? {
        /*
        val ftList = this.ftRepo.selectAll()

        var ftCheckedList: ArrayList<FavoriteTypeData>? = ArrayList()
        for (item in ftList) {
            if (item.checked) ftCheckedList?.add(item)
        }

        var selectedFt: FavoriteTypeData? = null

        ftCheckedList?.let {
            val ftSize = ftCheckedList.size

            // 선호하는 브랜드 , 종류를 모두 고른 유저라면

            if (ftSize > 0) {
                ftCheckedList.let {
                    selectedFt = getRandomInFt(it)
                }
                return selectedFt?.typeName
            } else {
                return "empty"
            }
        }
*/
        return "empty"
    }

    fun randomPick(): Int {
        /*
        val fbList = this.fbRepo.selectAll()
        val ftList = this.ftRepo.selectAll()

        var fbCheckedList: ArrayList<FavoriteBrandData>? = ArrayList()
        for (item in fbList) {
            if (item.checked) fbCheckedList?.add(item)
        }
        var ftCheckedList: ArrayList<FavoriteTypeData>? = ArrayList()
        for (item in ftList) {
            if (item.checked) ftCheckedList?.add(item)
        }

        var selectedFb: FavoriteBrandData? = null
        var selectedFt: FavoriteTypeData? = null

        ifNotNull(fbCheckedList, ftCheckedList) { fbCheckedList, ftCheckedList ->
            val fbSize = fbCheckedList.size
            val ftSize = ftCheckedList.size

            // 선호하는 브랜드 , 종류를 모두 고른 유저라면
            if (fbSize > 0 && ftSize > 0) {
                fbCheckedList.let {
                    selectedFb = getRandomInFb(it)
                }

                ftCheckedList.let {
                    selectedFt = getRandomInFt(it)
                }
            } else {

            }
        }

        Log.d("골라진 것 : ", " : " + selectedFb?.uid + ", " + selectedFt?.uid)
*/
        return 1
    }

    /*
    fun getRandomInFb(list: ArrayList<FavoriteBrandData>): FavoriteBrandData {
        val rand = Random()
        return list.get(rand.nextInt(list.size))
    }

    fun getRandomInFt(list: ArrayList<FavoriteTypeData>): FavoriteTypeData {
        val rand = Random()
        return list.get(rand.nextInt(list.size))
    }

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R) {
        if (a != null && b != null) {
            code(a, b)
        }
    }*/
}