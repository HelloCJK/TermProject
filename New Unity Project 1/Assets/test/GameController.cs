using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
using System.IO;

public class GameController : MonoBehaviour {

    public GameObject[] hazard;
    public Vector3 spawnPos_Min;
    public Vector3 spawnPos_Max;
    public float spawnRate;

    private float nextSpawn;

    public GUIText ScoreText;
    public GUIText DistanceText;
    public GUIText RestartText;
    public GUIText GameOverText;
    public GUIText GameOverText1;
    public GUIText SCB;

    private int score;
    private int distance;
    private bool restart;
    private bool gameOver;

    private int gameLevel = 0;

    // Use this for initialization
    void Start () {
        score = 0;
        distance = 0;
        restart = false;
        gameOver = false;
        RestartText.text = "";
        GameOverText.text = "";
        GameOverText1.text = "";
        SCB.text = "";
        gameLevel = 0;
        SetScore();
        Cursor.lockState = CursorLockMode.Locked;
        // GameObject moverObject = GameObject.FindWithTag("Mover");
        //SpawnWave();
	}

    void Update()
    {
        if (Application.platform == RuntimePlatform.Android)
            if (Input.GetKey(KeyCode.Escape))
                Application.Quit();

        if (Time.time > nextSpawn   && !gameOver)
        {
            SpawnWave();
            AddScore(10);
            AddDistance(1);
            nextSpawn = Time.time + spawnRate;
        }
        
        if ((Input.GetKeyDown(KeyCode.Mouse1) && restart)|| Input.GetKeyDown(KeyCode.R) || (Input.GetTouch(0).phase == TouchPhase.Began && restart))
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        }
        if(distance >= 10*(gameLevel+1))
        {
            spawnRate -= (float)0.1;
            gameLevel++;
            if (spawnRate <= (float)0.1) spawnRate = (float)0.1;
        }
    }
    void SpawnWave()
    {
        int n = Random.Range(0,3);
        Vector3 spawnPosition = new Vector3(Random.Range(spawnPos_Min.x, spawnPos_Max.x), Random.Range(spawnPos_Min.y, spawnPos_Max.y), spawnPos_Min.z);
        Quaternion spawnRotation = Quaternion.identity;

        GameObject astroid = Instantiate(hazard[n], spawnPosition, spawnRotation) as GameObject;

        float tmp_speed = astroid.GetComponent<Mover>().speed;

        astroid.GetComponent<Mover>().speed = tmp_speed - gameLevel;
    }

    public void AddScore(int newScore)
    {
        score += newScore;
        SetScore();
    }
    void SetScore()
    {
        ScoreText.text = "Score : " + score;
    }

    public void AddDistance(int newDistance)
    {
        distance += newDistance;
        SetDistance();
    }
    void SetDistance()
    {
        DistanceText.text = "Distance : " + distance + "km";
    }

    public void SetRestart()
    {
        RestartText.text = "Press TOUCH to restart";
        StartCoroutine(RestartDelay());
        //restart = true;
    }
    public void SetGameOver()
    {
        GameOverText1.text = "Game Over";
        StartCoroutine(GameOverDelay());
        //gameOver = true;
    }

    public struct ScoreBoardText
    {
        public int score;
        public string str;
    }

    public void SetScoreBoard()
    {
        //System.Threading.Thread.Sleep(5000);
        //string path = @"D:\Unity3D_M\test.txt";
        string path = Application.persistentDataPath + "/Ranking.dat";
        string s;
        ScoreBoardText[] sbText = new ScoreBoardText[20];
        int index = 0;

        char[] name_tmp = { (char)Random.Range('A', 'Z'), (char)Random.Range('A', 'Z'), (char)Random.Range('A', 'Z') };
        string name = new string(name_tmp);

        SCB.text = "ScoreBoard";
        GameOverText.text = "";

        using (StreamWriter sw = File.AppendText(path))
        {
            sw.WriteLine(name + " " + score);
        }

        using (StreamReader sr = File.OpenText(path))
        {
            while ((s = sr.ReadLine()) != null)
            {
                string[] sTmp = s.Split(' ');
                sbText[index].str = sTmp[0];
                if (sTmp[1] != null)
                {
                    sbText[index++].score = int.Parse(sTmp[1]);
                }
            }
        }
        sbSorting(sbText, index);
        for (int i = 0; i < index && i < 10; i++)
        {
            GameOverText.text += (i + 1) + ". \t" + sbText[i].str + " \t" + sbText[i].score + "\n";
        }
        using (StreamWriter sw = File.CreateText(path))
        {
            for (int i = 0; i < 10; i++)
                sw.WriteLine(sbText[i].str+" " + sbText[i].score);
        }
    }
    public ScoreBoardText[] sbSorting(ScoreBoardText[] sbText, int size)
    {
        ScoreBoardText index = sbText[0];
        int j = 0;

        for (int i = 0; i < size; i++)
        {
            index = sbText[i];
            j = i;

            while ((j > 0) && (sbText[j - 1].score < index.score))
            {
                sbText[j] = sbText[j - 1];
                j = j - 1;
            }
            sbText[j] = index;
        }

        return sbText;
    }
    IEnumerator GameOverDelay()
    {
        yield return new WaitForSeconds(1.0f);
        gameOver = true;
    }
    IEnumerator RestartDelay()
    {
        yield return new WaitForSeconds(1.0f);
        restart = true;
    }
}
