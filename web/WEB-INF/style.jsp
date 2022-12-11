<style>
    * {
        box-sizing: border-box;
        padding: 0;
        margin: 0;
        font-family: sans-serif;
    }

    nav, .header {
        width: 100%;
        background-color: #8a817c;
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.479);
    }

    ul, .header {
        list-style-type: none;
        margin: 0 auto;
        max-width: 1220px;
        padding: 0 1em;
        overflow: hidden;

    }

    .headerTitle {
        display: block;
        color: #f4f3ee;
        padding: 1em 0;
    }

    .userName {
        margin-bottom: 1.5em;
        font-weight: bolder;
        color: #8a817c;
    }

    li {
        float: left;
        font-weight: bold;
    }

    li a  {
        display: block;
        color: #f4f3ee;
        text-align: center;
        text-decoration: none;
        padding: 1em 1.5em;
    }

    li :hover:not(.active) {
        background-color: #e0afa0;
        color: #463f3a;
    }

    li.active {
        background-color: #bcb8b1;
        color: #463f3a;
    }

    main {
        max-width: 1220px;
        margin: 1em auto;
        padding: 0 1em;
    }

    table {
        border-collapse: collapse;
        width: 80%;
        margin: 0.5em 0;
        background-color: #f4f3ee;
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.179);
    }

    th {
        font-size: 1rem;
        background-color: #bcb8b1;
        color: #463f3a;
        text-align: left;
    }

    td, th {
        text-align: left;
        padding: 0.35em 0.5em;
    }

    td, input, select, button {
        font-size: 1em;
    }

    input, button, select {
        padding: 0.25em;
        margin: 0.25em 0 0 0;
    }

    .delete {
        height: 20px;
        width: 20px;
    }

    h3 {
        padding: 1.75em 0 0.05em 0;
    }

    p {
        line-height: 1.75em;
    }

    #404 {
        background-color: #f4f3ee;
        color: #463f3a;
        margin: 50px auto;
        border: 5px solid #463f3a;
        height: 200px;
        width: 200px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    #404 p {
        font-weight: bold;
    }

</style>
