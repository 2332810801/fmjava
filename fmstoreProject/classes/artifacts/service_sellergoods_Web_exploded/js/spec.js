var vm = new Vue({
    el: "#app",
    data: {
        specList: [],//专门存放规格的数据
        spec: {//规格实体
            specName: ''//规格名称
        },
        page: 1,//当前页码
        pageSize: 10,//一页有多少条数据
        total: 0,//总记录数
        maxPage: 9,//最大分页数
        selectedId: [],//当前记录 checkbox选中要删除的ID
        searchSpec: { //查询的规格实体
            specName: '' //规格名称
        },
        specEntity: {//添加的规格以及规格选项的实体
            spec: {},//规划名称集合
            specOption: []//规格选项集合
        }
    },
    methods: {
        //分页条件查询
        pageHandler: function (page) {
            var _this = this;//获取data的this
            this.page = page;//获取page的页码
            axios.post("/spec/search.do?page=" + this.page + "&pageSize=" + this.pageSize, this.searchSpec)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.specList = response.data.rows;//返回的数据
                    _this.total = response.data.total;//返回的总记录数
                }).catch(function (reason) {//失败返回
                console.log(reason);
            })
        },
        //添加规格选项
        addRow: function () {
            this.specEntity.specOption.push({});
        },
        //根据角标索引 删除规格选项
        deleteRow: function (index) {
            this.specEntity.specOption.splice(index, 1);
        },
        //添加规格
        save: function () {
            var url = '';
            if (this.specEntity.spec.id != null) {//判断规格id是否存在 存在：修改操作，不存在：增加操作
                url = "/spec/update.do";//更新
            } else {
                url = "/spec/save.do";//添加
            }

            var _this = this;//获取data的this
            axios.post(url, this.specEntity)
                .then(function (response) {//传入添加的规格以及规格选项的实体
                    if (response.data.success) {
                        _this.specEntity.spec = {};//清空
                        _this.specEntity.specOption = [];//清空
                        _this.pageHandler(1);
                    } else {

                    }
                }).catch(function (reason) {//失败返回
                console.log(reason);
            })
        },
        //根据id查找规格
        findSpecWithId: function (id) {
            var _this = this;//获取data的this
            axios.get("/spec/findSpecWithId.do?id=" + id)//向服务器发送get请求 获取规格信息
                .then(function (response) {
                    _this.specEntity = response.data;//获得数据绑定道specEntity实体
                }).catch(function (reason) {//失败返回
                console.log(reason);
            })
        },
        /**
         * @param event 当前checkbox的所有信息
         * @param id 选中要删除的id
         */
        deleteSelection: function (event, id) {
            //判断 当前复选框checkbox是否为选中状态
            if (event.target.checked) {
                //选中
                this.selectedId.push(id);//向记录添加选中的ID信息
            } else {
                //取消选中
                //移除选中元素
                var idx = this.selectedId.indexOf(id);//获取点击的id位置
                this.selectedId.splice(idx, 1)//删除已选中的ID
            }
            console.log(this.selectedId);
        },
        //删除规格
        deleteSpec: function () {
            var _this = this;
            axios.post("/spec/delete.do", Qs.stringify({idx: this.selectedId}, {indices: false})).then(function (response) {
                console.log(response.data);
                if (response.data.success) {//如果删除成功
                    alert(response.data.message);//删除成功后返回提示信息
                    _this.pageHandler(1);//删除成功后调用查询所有的规格方法
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