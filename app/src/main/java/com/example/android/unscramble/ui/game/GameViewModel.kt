package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = 0
    val score: Int get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int get() = _currentWordCount

    private lateinit var _currentScrambleWord: String
    val currentScrambleWord: String get() = _currentScrambleWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed")
    }

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while(String(tempWord).equals(currentWord, false)){
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambleWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun isUserWordCorrect(playerWord: String): Boolean{
        return if(playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun nextWord(): Boolean{
        return if(_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false
    }

    fun reinitializedData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}