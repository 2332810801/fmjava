Vue.component('v-select', VueSelect.VueSelect);
new Vue({
    el:"#app",
    data:{
        tempList:[],//模板数据
        searchTemp:{},
        page: 1,  //显示的是哪一页
        pageSize: 5, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:9,
        selectedId:[],//记录选择了哪些记录的id
        //下拉列表：品牌
        brandsOptions: [],//品牌绑定到下拉框的数据
        id:'',//类型id
        addname:"",//添加的类型
        placeholder: '可以进行多选',
        selectBrands: [],//选中的数据
        sel_brand_obj: [],//选中的value数据
        //下拉列表：规格
        specOptions: [],//规格 下拉框的数据
        selectSpecs: [],//选中的数据
        sel_spec_obj: [],//选中的value数据
        //拓展属性
        custom:[]//拓展数据

    },
    methods : {
        //分页条件查询
        pageHandler: function (page) {
            var _this = this;//获取data的this
            this.page = page;//获取page的页码
            axios.post("/temp/search.do?page=" + this.page + "&pageSize=" + this.pageSize, this.searchTemp)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.tempList = response.data.rows;//返回的数据
                    _this.total = response.data.total;//返回的总记录数

                }).catch(function (reason) {//失败返回
                console.log(reason);
            })
        },
        /**
         * [{"id":4,"text":"小米"},{"id":2,"text":"华为"}]
         * [{"id":42,"text":"选择颜色"},{"id":43,"text":"选择版本"},{"id":44,"text":"套　　餐"}]
         * 把json数据转换为string
         */
        jsonTostring:function(jsonString,key) {
            //把字符串转换成json
            var jsonObj=JSON.parse(jsonString);
            var value='';//定义value 存储取得值
            for(var i=0;i<jsonObj.length; i++){//循环json 取值value
                if(i>0){//当i>0时 拼接 '，'号
                    value +=",";
                }
                value +=jsonObj[i][key];//取值 通过key:text; 取值 小米 循环追加
            }
            return value;//返回
        },
        //选中下拉框调用的事件
        selected_brand: function(values){
            this.selectBrands =values.map(function(obj){//获取下拉框的信息
                return obj.id//获取下拉框的id
            });

            //console.log(this.selectBrands);
            //console.log(this.sel_brand_obj)//设置值
        },
        //加载关联品牌的下拉框数据
        selLoadData:function () {
            _this = this;
            //向服务器发送请求获取品牌信息
            //下拉框：品牌
            axios.get("/brand/selectOptionList.do")
                .then(function (response) {
                    _this.brandsOptions = response.data;//把品牌信息复制给brandsOptions
                }).catch(function (reason) {
                console.log(reason);
            });
            //下拉框：规格
            axios.get("/spec/selectOptionList.do")
                .then(function (response) {
                    _this.specOptions = response.data;//把规格信息复制给specOptions
                }).catch(function (reason) {
                console.log(reason);
            });
        },
        //选中规格下拉框后调用的方法
        selecte_spec: function(values){
            this.selectSpecs =values.map(function(obj){
                return obj.id
            });
            //console.log(this.sel_spec_obj);
        },
        save:function () {
            var url='';
            if(this.id.length  <=0){
                url ="/temp/add.do";
            }else{
                url="/temp/update.do";

            }

            var entity = {
                id:this.id,//获取id
                name:this.addname,//获取名称
                specIds:this.sel_spec_obj,//获取规格
                brandIds:this.sel_brand_obj,//获取品牌
                customAttributeItems:this.custom//获取其他属性
            };
            axios.post(url,entity)
                .then(function (response) {
                    if(response.data.success){//添加or修改成功
                        alert(response.data.message);
                        _this.id="";
                        _this.pageHandler(1);
                    }else {//失败
                        alert(response.data.message);
                    }
                    _this.pageHandler(1);
                }).catch(function (reason) {
                console.log(reason);
            });
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
        //删除模块
        deleteTemp:function() {
            var _this = this;
            axios.post("/temp/delete.do", Qs.stringify({idx: this.selectedId}, {indices: false})).then(function (response) {
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
        //查询要修改模块
        findById:function(id) {
            var _this = this;//获取data的this
            axios.get('/temp/findById.do', {params: {id: id}}).then(function (response) {
                //取服务端响应的结果
                console.log(response)
                _this.id=response.data.id;
                _this.addname=response.data.name;
            }).catch(function (reason) {//失败返回
                console.log(reason);
            })
        },
        //清除id
        clearId:function() {
            this.id="";
        },
        //新增拓展属性列表
        addRow:function() {
            this.custom.push({});
        },
        //根据角标索引 删除规格选项
        deleteRow: function (index) {
            this.custom.splice(index, 1);
        },
    },
    created: function() {
        this.pageHandler(1);
        this.selLoadData();
    }
});