var vm = new Vue({
    el: "#app",
    data: {
        brandList: [],//专门存放品牌的数据
        brand: {//品牌实体 用于添加 &&修改
            name: '',
            firstChar: ''
        },
        page: 1,//当前页码
        pageSize: 10,//一页有多少条数据
        total: 100,//总记录数
        maxPage: 9,//最大分页数
        selectedId:[],//当前记录 checkbox选中要删除的ID
        searchBrand : { //查询的品牌实体
            name: '',
            firstChar: ''
        }
    },
    methods: {
        //请求所有的品牌数据
        findAllBrand: function () {
            var _this = this;//获取data的this
            axios.get("/brand/findAllBrands.do").then(function (response) {
                //console.log(response.data);
                _this.brandList = response.data;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        //编辑品牌
        brandSave: function () {
            var url='';
            if(this.brand.id!=null){//判断品牌是否有id 没有id就是添加操作 有id就是更新操作
                //更新
                url='/brand/update.do';
            }else{
                //添加
                url='/brand/add.do';
            }

            var _this = this;//获取data的this
            //获取数据添加品牌
            axios.post(url, this.brand).then(function (response) {
                console.log(response.data);
                if (response.data.success) {
                    alert(response.data.message);
                    _this.pageHandler(1);
                } else {
                    alert(response.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        //分页条件查询
        pageHandler: function (page) {
            var _this = this;//获取data的this
            this.page = page;//获取page的页码
            axios.post("/brand/findPage.do?page="+this.page+"&pageSize="+this.pageSize, this.searchBrand)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.brandList = response.data.rows;//返回的数据
                    _this.total = response.data.total;//返回的总记录数
                }).catch(function (reason) {//失败返回
                     console.log(reason);
            })
        },
        //根据ID查询单个品牌
        findById : function(id) {
            var _this = this;//获取data的this
            axios.get('/brand/findById.do', {params: {id: id}}).then(function (response) {
                    //取服务端响应的结果
                    _this.brand=response.data;//把查出来的值 绑定到实体 然后通过双向绑定道文本框
                }).catch(function (reason) {//失败返回
                console.log(reason);
            })
        },
        //
        /**
         * @param event 当前checkbox的所有信息
         * @param id 选中要删除的id
         */
        deleteSelection:function(event,id) {
            //判断 当前复选框checkbox是否为选中状态
            if(event.target.checked){
                //选中
                this.selectedId.push(id);//向记录添加选中的ID信息
            }else{
                //取消选中
                //移除选中元素
                var idx=this.selectedId.indexOf(id);//获取点击的id位置
                this.selectedId.splice(idx,1)//删除已选中的ID
            }
            console.log(this.selectedId);
        },
        //删除品牌
        deleteBrand:function () {
            var _this=this;
            axios.post("/brand/delete.do", Qs.stringify({idx:this.selectedId},{indices : false})).then(function (response) {
                console.log(response.data);
                if (response.data.success) {//如果删除成功
                    alert(response.data.message);//删除成功后返回提示信息
                    _this.pageHandler(1);//删除成功后调用查询所有的品牌方法
                } else {
                    alert(response.data.message);//删除失败提示信息
                }
            }).catch(function (reason) {//方法调用失败提示
                console.log(reason);
            })
        },
    },
    created: function () {//vue对象初始化完成后自动调用
        this.pageHandler(1);//初始化后调用
    }
});