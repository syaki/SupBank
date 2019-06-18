<template>
  <div class="header">
    <header role="banner">
      <div class="header-inner">
        <div class="header-brand">
          <router-link to="/" class="homeEntry">SUPBANK</router-link>
        </div>
        <div class="header-searchBar" role="search">
          <form action class="searchForm">
            <div class="search-inner">
              <input
                type="text"
                class="search-input"
                name="search-input"
                placeholder="Lookup blocks, transactions, hash..."
                v-model="searchText"
              >
              <button class="search-btn" type="button" v-on:click="search">
                <svg class="icon" fill="currentColor" viewBox="0 0 24 24" width="18" height="18">
                  <path
                    d="M17.068 15.58a8.377 8.377 0 0 0 1.774-5.159 8.421 8.421 0 1 0-8.42 8.421 8.38 8.38 0 0 0 5.158-1.774l3.879 3.88c.957.573 2.131-.464 1.488-1.49l-3.879-3.878zm-6.647 1.157a6.323 6.323 0 0 1-6.316-6.316 6.323 6.323 0 0 1 6.316-6.316 6.323 6.323 0 0 1 6.316 6.316 6.323 6.323 0 0 1-6.316 6.316z"
                    fill-rule="evenodd"
                  ></path>
                </svg>
              </button>
            </div>
          </form>
        </div>
        <div class="header-wallet">
          <div class="walletName" v-if="walletName">
            <span class="walletEntry">{{walletName}}</span>
            <button class="signout" v-on:click="signout">Sign out</button>
          </div>
          <div class="signWallet" v-else>
            <router-link to="/signin" class="signin">Sign In</router-link>
            <router-link to="/signup" class="signup">Sign Up</router-link>
          </div>
        </div>
      </div>
    </header>
  </div>
</template>

<script>
export default {
  name: "AppHeader",
  data() {
    return {
      walletName: null,
      searchText: ""
    };
  },
  created() {
    var walletName = localStorage.getItem("wallet_name");

    if (!walletName) {
      this.walletName = null;
    } else {
      this.walletName = walletName;
    }
  },
  methods: {
    search: function() {
      if (!this.searchText || this.searchText.length <= 0) {
        return;
      }

      this.$router.push({
        path: "search",
        query: {
          q: this.searchText
        }
      });
    },
    signout: function() {
      this.$axios({
        method: "post",
        url: "http://192.168.1.103:8990/user/logout"
      })
        .then(response => {
          const data = response.data;
          if (data.status.Ack === "success") {
            localStorage.removeItem("wallet_name");
            this.$router.go(0);
          } else {
            alert(data.status.ErrorMessage);
          }
        })
        .catch(error => {
          alert(error);
        });
    }
  }
};
</script>

<style scoped>
.header {
  position: relative;
  z-index: 100;
  min-width: 1032px;
  overflow: hidden;
  background: #fff;
  -webkit-box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  -ms-box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  background-clip: content-box;
}

.header-inner {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  width: 1000px;
  height: 52px;
  padding: 0 16px;
  margin: 0 auto;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.header-brand {
  margin: 0 12px;
}

.header-brand .homeEntry {
  font-size: 36px;
  color: #0084ff;
  font-family: Herculanum;
}

.header-searchBar {
  margin-left: 130px;
}

.header-searchBar .search-inner {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  width: 340px;
  height: 34px;
  padding: 4px 10px;
  font-size: 14px;
  background: #f6f6f6;
  border: 1px solid #ebebeb;
  border-radius: 3px;
  -webkit-box-sizing: border-box;
  -ms-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: background 200ms, border 200ms;
  -ms-transition: background 200ms, border 200ms;
  transition: background 200ms, border 200ms;
}

.header-searchBar .search-input {
  border: 0;
  width: 300px;
  background: #f6f6f6;
  color: #1a1a1a;
  height: 24px;
  line-height: 24px;
  font-size: 14px;
  overflow: hidden;
  cursor: text;
  outline: none;
}

.header-searchBar .search-btn {
  border: 0;
  background: #f6f6f6;
  outline: none;
}

.header-wallet {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-pack: end;
  -ms-flex-pack: end;
  justify-content: flex-end;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.header-wallet .signin,
.header-wallet .signup,
.header-wallet .walletEntry {
  padding: 5px 10px;
  font-size: 16px;
  line-height: 30px;
  color: #444444;
  background-color: #f6f6f6;
  border-radius: 4px;
}

.header-wallet .signout {
  padding: 0 10px;
  height: 28px;
  font-size: 16px;
  line-height: 16px;
  color: #444444;
  background-color: #f6f6f6;
  border-radius: 4px;
  border: none;
}

.header-wallet .signin,
.header-wallet .walletEntry {
  margin-right: 12px;
}
</style>
