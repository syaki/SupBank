<template>
  <div class="Transaction">
    <AppHeader/>
    <div class="main">
      <main role="main">
        <div class="main-inner">
          <div class="transactionCard card">
            <table class="listTable">
              <caption>Transaction detail:</caption>
              <tr>
                <td>ID:</td>
                <td>{{transactionData.transactionid}}</td>
              </tr>
              <tr>
                  <td>Block id:</td>
                  <td>{{transactionData.blockid}}</td>
              </tr>
              <tr>
                <td>Input:</td>
                <td>{{transactionData.input}}</td>
              </tr>
              <tr>
                <td>Output:</td>
                <td>{{transactionData.output}}</td>
              </tr>
              <tr>
                <td>Sum:</td>
                <td>{{transactionData.sum}}</td>
              </tr>
              <tr>
                <td>Time:</td>
                <td>{{`${new Date(transactionData.timestamp).getFullYear()}/${new Date(transactionData.timestamp).getMonth()}/${new Date(transactionData.timestamp).getDate()} ${new Date(transactionData.timestamp).getHours()}:${new Date(transactionData.timestamp).getMinutes()}`}}</td>
              </tr>
            </table>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "transaction",
  components: {
    AppHeader
  },
  data() {
    return {
      id: this.$route.query.q,
      transactionData: {}
    };
  },
  created() {
    var that = this;
    const id = this.$route.query.q;

    this.$axios({
      method: "post",
      url: "http://192.168.1.103:8990/transaction/getTransactionById",
      data: {
        transactionId: id
      }
    })
      .then(response => {
        const data = response.data;
        if (data.status.Ack === "success") {
          that.transactionData = data.transactionList[0];
        } else {
          alert(data.status.ErrorMessage);
        }
      })
      .catch(error => {
        alert(error);
      });
  }
};
</script>

<style scoped>
.main-inner {
  position: relative;
  width: 1000px;
  padding: 0 16px;
  margin: 14px auto;
}

.searchCard .desc {
  margin-top: 18px;
  margin-bottom: 30px;
  font-size: 18px;
  color: #8590a6;
}

.listTable {
  display: flex;
  display: -webkit-box;
  display: -ms-flexbox;
  align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  justify-content: center;
  width: 100%;
}

.listTable caption {
  font-size: 24px;
  margin-bottom: 16px;
  color: #0084ff;
  text-align: left;
  width: 920px;
}

.listTable td {
  padding: 4px 8px;
}
</style>
