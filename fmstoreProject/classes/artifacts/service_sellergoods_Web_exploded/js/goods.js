new Vue({
    el:"#app",
    data:{
        categoryList1:[],//分类1数据列表
        categoryList2:[],//分类2数据列表
        categoryList3:[],//分类3数据列表
        grade:1,  //记录当前级别
        catSelected1:-1,//分类1选中的id
        catSelected2:-1,//分类2选中的id
        catSelected3:-1,//分类3选中的id,
        typeId:0,//模板id
        brandList:[],//品牌数据
        selBrand:-1,//当前选中的品牌
        curImagesObj:{
            color:"",
            url:""
        },
        ImageList:[],
        specList:[]
    },
    methods:{
        //商品分类加载数据
        loadCateData: function (id) {
            _this = this;
            axios.post("/itemCat/findByParentId.do?parentId="+id)
                .then(function (response) {
                    if (_this.grade == 1){//当级别是1地时候
                        //取服务端响应的结果
                        _this.categoryList1 = response.data;
                    }
                    if (_this.grade == 2){//当级别是2地时候
                        //取服务端响应的结果
                        _this.categoryList2 =response.data;
                    }
                    if (_this.grade == 3){//当级别是3地时候
                        //取服务端响应的结果
                        _this.categoryList3 =response.data;
                    }
                }).catch(function (reason) {
                console.log(reason);
            })
        },
        //二级商品分类选项发生改变
        getCatSelected:function (grade) {//选项改变时调用

            if (grade == 1){
                this.categoryList2 = [];//清空二级分类数据
                this.catSelected2=-1;   //默认选择
                this.categoryList3 = []; //清空三级分类数据
                this.catSelected3=-1; //默认选择
                this.grade = grade + 1;// 加载第2级的数据
                this.loadCateData(this.catSelected1);
            }
            if(grade == 2) { //第2级选项改变
                this.categoryList3 = []; //清空三级分类数据
                this.catSelected3=-1; //默认选择

                this.grade = grade + 1;// 加载第3级的数据
                this.loadCateData(this.catSelected2);
            }
            if (grade == 3){ //第3级选项改变
                var _this=this;
                //加载模板
                axios.post("/itemCat/findOnegory.do?id="+this.catSelected3)
                    .then(function (response) {
                        _this.typeId = response.data.typeId;
                    }).catch(function (reason) {
                    console.log(reason);
                })
            }
        },
        uploadFile : function(){
            var _this=this;
            //图片上传
           var foormData= new FormData();//创建form表单
            foormData.append("file",file.files[0])//获取名称为file的内容
            const instance=axios.create({//打开form凭证
               withCredentials:true
            });
            instance.post("/upload/uploadFile.do",foormData).then(function(response){

                if(response.data.success){
                    _this.curImagesObj.url=response.data.message;
                }else{
                    alert("上传失败")
                }
            }).catch(function(reason) {
                alert("上传失败")
            })
        },
        saveImage:function() {//保存图片
            if(this.curImagesObj.url==''||this.curImagesObj.color==''){//如果没有填写颜色，或没有上传图片
                alert("请输入颜色或上传图片")
                return;
            }
            var obj={color:this.curImagesObj.color,url:this.curImagesObj.url}
            this.ImageList.push(obj);//保存到ImageList
            this.curImagesObj.color='';//清空
            this.curImagesObj.url='';//清空
        },
        deleteImage:function(url,index) {
            var _this=this;
            //删除图片
            axios.get("/upload/deleteImg.do?url="+url).then(function(response){
                if(response.data.success){
                    alert(response.data.message);
                    _this.ImageList.splice(index,1);//删除list的元素
                }else {
                    alert(response.data.message);
                }
            }).catch (function(reason) {

            })
        }
    },
    watch:{//页面发生改变时电影 获取改变的值和未改变的值
        typeId:function (newValue,oldValue) {
            var _this = this;
            _this.brandList =[];
            _this.selBrand = -1;
            axios.post("/temp/findOne.do?id="+newValue)
                .then(function (response) {
                    console.log(response.data);
                    _this.brandList = JSON.parse(response.data.brandIds);//把json格式转换成字符串
                }).catch(function (reason) {
                console.log(reason);
            });

            _this.specList=[];//清空
            //加载规格和规格选项
            axios.post("/temp/findBySpecWithID.do?id="+newValue).then(function(){
                _this.specList=response.data;
                console.log(response.data);
            }).catch (function(reason) {

            })
        }
    },
    created: function() {
        this.loadCateData(0);
    }
});
