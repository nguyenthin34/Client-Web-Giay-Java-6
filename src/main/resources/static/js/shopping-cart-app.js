const app = angular.module("shopping-cart-app", []);


app.controller("shopping-cart-ctrl", function ($scope, $http) {

    /*
    * Quản lý giở hàng
    * */
    $scope.cart = {
        items: [],

        add(id){
            var item = this.items.find(item => item.product_id == id);

            if(item != null){
                item.quantity++;
                this.saveToLocalStorage();
            }else{
                $http.get(`/api/product/${id}`).then(response => {
                    response.data.quantity = 1;
                    this.items.push(response.data);
                    this.saveToLocalStorage();
                })
            }
        },

        remove(id){
            var index = this.items.findIndex((item => item.product_id == id));
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },

        clear(){
            this.items = []
            this.saveToLocalStorage()
        },

        amt_of(item){},

        get count(){
            return this.items
                .map(item => item.quantity)
                .reduce((total, quantity) => total += quantity, 0)
        },

        get amount(){
            return this.items
                .map(item => item.quantity  * item.price)
                .reduce((total, quantity) => total += quantity, 0);
        },

        saveToLocalStorage(){
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },

        loadFromLocalStorage(){
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        }

    }
    $scope.cart.loadFromLocalStorage();

    $scope.order = {
        createDate: new Date(),
        address: "",
        phone: "",
        name:"",
        email: "",
        status: 1,
        totalAmount: 100,
        user: $("#username").text(),
        get orderDetails(){
            return $scope.cart.items.map(item => {
                return {
                    product_id: item.product_id,
                    price: item.price,
                    quantity: item.quantity
                }
            });
        },
        purchase(){
            var order = angular.copy(this);

            $http.post("/api/orders", order).then(
                response => {
                    alert("Đặt hàng thành công");
                    $scope.cart.clear();
                    location.href = "/order/detail/" + response.data.order_id;
                }
            ).catch(error => {
                alert("Đặt hàng lỗi!")
                console.log(error)
            })
        }
    }
})