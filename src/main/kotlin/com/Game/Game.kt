package com.Game

class Game {
    private var table: Array<Array<Int>>? = null
    private var tableHeight: Int = 0
    private var tableWidth: Int = 0
    private var neightborsToBorn: Int = 0
    private var neightborsToLiveLeftbound: Int = 0
    private var neightborsToLiveRightbound: Int = 0

    fun setParamsFromData(data : GameStateData) {
        setTable(data.table,data.width,data.height)
        setParams(data.neightborsToBorn,data.neightborsToLiveLeftbound,data.neightborsToLiveRightbound)
    }

    fun setTable(table: Array<Array<Int>>,tableWidth : Int,tableHeight : Int) {
        this.table = table
        this.tableHeight = tableHeight
        this.tableWidth = tableWidth
    }

    fun setParams(neightborsToBorn: Int, neightborsToLiveLeftbound: Int,neightborsToLiveRightbound: Int) {
        this.neightborsToBorn = neightborsToBorn
        this.neightborsToLiveLeftbound = neightborsToLiveLeftbound
        this.neightborsToLiveRightbound = neightborsToLiveRightbound
    }


    fun makeStep() : ServerResponseData {
        var isActed = false
        val temptable: Array<Array<Int>> =
            Array(tableHeight) { Array(tableWidth) { 0 } }
        for (i in 0 until tableHeight) {
            for (j in 0 until tableWidth) {
                var counter: Int = 0
                for (h in -1..1) {
                    for (w in -1..1) {
                        var x: Int = j + w
                        var y: Int = i + h
                        if (x < 0) x += tableWidth
                        if (y < 0) y += tableHeight
                        if (x >= tableWidth) x = 0
                        if (y >= tableHeight) y = 0
                        if (!(w == 0 && h == 0) && table!![y][x] > 0) counter++
                    }
                }
                if (counter == neightborsToBorn && table!![i][j] == 0) {
                    isActed = true
                    temptable[i][j] = 1
                } else if (table!![i][j] > 0) {
                    if (counter in neightborsToLiveLeftbound..neightborsToLiveRightbound) {
                        temptable[i][j] = table!![i][j] + 1
                    } else {
                        if (table!![i][j] != 0) {
                            isActed = true
                        }
                        temptable[i][j] = 0
                    }
                }
            }
        }
        for (i in 0 until tableHeight) {
            for (j in 0 until tableWidth) {
                table!![i][j] = temptable[i][j];
            }
        }
        return ServerResponseData(isActed,table)
    }
}