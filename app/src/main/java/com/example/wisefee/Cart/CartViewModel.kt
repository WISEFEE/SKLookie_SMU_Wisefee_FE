package com.example.wisefee.Cart



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<CartItem>>()
    val cartItems: LiveData<MutableList<CartItem>> = _cartItems

    init {
        _cartItems.value = mutableListOf()
    }

    fun addToCart(cartItem: CartItem) {
        val currentItems = _cartItems.value ?: mutableListOf()
        val existingItem = currentItems.find {
            it.product.productId == cartItem.product.productId &&
                    it.temperature == cartItem.temperature
        }

        if (existingItem != null) {
            // 이미 장바구니에 있는 경우 수량을 업데이트
            existingItem.quantity += cartItem.quantity
        } else {
            // 장바구니에 없는 경우 새로 추가
            currentItems.add(cartItem)
        }

        _cartItems.postValue(currentItems)
    }

    fun updateCartItemQuantity(productId: Int, newQuantity: Int, temperature: String) {
        val currentItems = _cartItems.value ?: return
        val itemToUpdate = currentItems.find {
            it.product.productId == productId &&
                    it.temperature == temperature
        }

        itemToUpdate?.quantity = newQuantity

        _cartItems.postValue(currentItems)
    }
}
