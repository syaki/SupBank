<template>
  <div id="transaction">
    <section class="card">
      <p class="title">Transactions Detail</p>
      <el-table :data="transactionList" stripe style="width: 100%">
        <el-table-column
          prop="timestamp"
          label="日期"
          width="160"
          fixed
        ></el-table-column>
        <el-table-column
          prop="transactionid"
          label="ID"
          width="80"
          fixed
        ></el-table-column>
        <el-table-column
          prop="blockid"
          label="Block ID"
          width="100"
          fixed
        ></el-table-column>
        <el-table-column prop="input" label="付款方"></el-table-column>
        <el-table-column prop="output" label="收款方"></el-table-column>
        <el-table-column
          prop="sum"
          label="金额"
          width="60"
          fixed="right"
        ></el-table-column>
      </el-table>
    </section>
  </div>
</template>

<script>
export default {
  name: "transaction",
  data() {
    return {
      id: this.$route.query.q,
      transactionList: []
    };
  },
  created() {
    this.id = this.$route.query.q;

    this.$axios({
      method: "post",
      url: "/transaction/getTransactionById",
      data: {
        transactionId: this.id
      }
    })
      .then(response => {
        const data = response.data;
        if (data.status.Ack === "success") {
          for (let i = 0; i < data.transactionList.length; i++) {
            let t = data.transactionList[i];
            let time = new Date(t.timestamp);
            t.timestamp =
              time.getFullYear() +
              "/" +
              (time.getMonth() + 1) +
              "/" +
              time.getDate() +
              "  " +
              (time.getHours() >= 10
                ? time.getHours()
                : `0{$time.getHours()}`) +
              ":" +
              (time.getMinutes() >= 10
                ? time.getMinutes()
                : `0{$time.getMinutes()}`) +
              ":" +
              (time.getSeconds() >= 10
                ? time.getSeconds()
                : `0{$time.getSeconds()}`);
          }
          this.transactionList = data.transactionList;
        } else {
          this.$message.error(data.status.ErrorMessage);
        }
      })
      .catch(() => {
        this.$message.error("获取数据失败");
      });
  }
};
</script>
