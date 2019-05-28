package com.kotlin.mypractice.knowledgebox.root


interface RootBusinessLogic

interface RootDataStore

/*
    CURRENTLY NO USE:
 */
class RootDataInteractor : RootBusinessLogic, RootDataStore {
  private lateinit var mPresenter: RootPresentationLogic
}
