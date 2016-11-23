using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class GameController : MonoBehaviour {

    public GameObject[] hazard;
    public Vector3 spawnPos;
    public float spawnRate;

    private float nextSpawn;

    public GUIText ScoreText;
    public GUIText DistanceText;
    public GUIText RestartText;
    public GUIText GameOverText;

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
        gameLevel = 0;
        SetScore();
        Cursor.lockState = CursorLockMode.Locked;
        // GameObject moverObject = GameObject.FindWithTag("Mover");
        //SpawnWave();
	}

    void Update()
    {
        if (Time.time > nextSpawn   && !gameOver)
        {
            SpawnWave();
            AddScore(10);
            AddDistance(1);
            nextSpawn = Time.time + spawnRate;
        }
        if ((Input.GetKeyDown(KeyCode.Mouse1) && restart)|| Input.GetKeyDown(KeyCode.R))
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
        Vector3 spawnPosition = new Vector3(Random.Range(-spawnPos.x,spawnPos.x), spawnPos.y, spawnPos.z);
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
        RestartText.text = "Press 'R' to restart";
        restart = true;
    }
    public void SetGameOver()
    {
        GameOverText.text = "Game Over";
        gameOver = true;
    }
}
